package com.aiden.vokamoka.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiden.vokamoka.data.model.Expression
import com.aiden.vokamoka.data.model.UserInfo
import com.aiden.vokamoka.data.model.WordCategory
import com.aiden.vokamoka.data.model.WordPool
import com.aiden.vokamoka.data.repository.ExpressionRepository
import com.aiden.vokamoka.data.repository.LanguageRepository
import com.aiden.vokamoka.data.repository.UserInfoRepository
import com.aiden.vokamoka.data.repository.WordCategoryRepository
import com.aiden.vokamoka.data.repository.WordPoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

@HiltViewModel
class PlainTextViewModel @Inject constructor(
    private val expRepo: ExpressionRepository,
    private val categoryRepo: WordCategoryRepository,
    private val langRepo: LanguageRepository,
    private val poolRepo: WordPoolRepository,
    private val userRepo: UserInfoRepository
) : ViewModel() {

    // * ----------------------------------------
    // *    Variables
    // * ----------------------------------------
    private val TAG = this.javaClass.simpleName

    private val _isAddWord: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    // * ----------------------------------------
    // *    Getter
    // * ----------------------------------------
    val isAddWord: LiveData<Boolean> get() = _isAddWord

    // * ----------------------------------------
    // *    Setter
    // * ----------------------------------------
    fun setIsAddWord(flag : Boolean) {
        this._isAddWord.postValue(flag)
    }

    // other method

    fun addTextTokens(editTextValue: String){
        viewModelScope.launch(Dispatchers.IO) {
            var reader:BufferedReader? = null
            var inputStream: ByteArrayInputStream? = null
            try {
                // 문자열을 InputStream으로 변환
                inputStream = ByteArrayInputStream(editTextValue.toByteArray(Charsets.UTF_8))
                // BufferedReader로 감싸기
                reader = BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8))

                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    if(line != null){
                        val tokens:List<String> = line.split("/")
                        if(tokens.size < 3) {
                            continue
                        }
                        // step 1. category 있는지 보기, 있다면 id
                        val categoryToken: String = tokens[2].trim()
                        var categoryId: Long = categoryRepo.queryCategoryId(categoryToken)
                        Log.d(TAG, "[$categoryToken] QUERY categoryId : $categoryId")

                        // step 2. 없다면 추가, 있다면 준비
                        if(categoryId < 0){
                            categoryId = categoryRepo.addEntity(
                                WordCategory(
                                    name = categoryToken
                                )
                            )
                            // temp 등록된 카테고리
                            Log.d(TAG, "NEW CATEGORY : ${categoryRepo.readEntity(categoryId)}")
                        }

                        // step 3. 표현 추가하기
                        val fromToken: String = tokens[0].trim()
                        val toToken: String = tokens[1].trim()

                        val fromExpression = Expression(
                            wordText = fromToken
                        )
                        val toExpression = Expression(
                            wordText = toToken
                        )
                        val fromId:Long = expRepo.addEntity(fromExpression)
                        val toId:Long = expRepo.addEntity(toExpression)

                        val expression1: Expression = expRepo.readEntity(fromId)
                        val expression2: Expression = expRepo.readEntity(toId)

                        Log.d(TAG, "EXP 1 $expression1")
                        Log.d(TAG, "EXP 2 $expression2")

                        // step 4. Word Pool 등록하기
                        val userId: Long = userRepo.readLastEntityId()

                        val mWordPool = WordPool(
                            fkUserId = if(userId < 0) null else userId,
                            originExpId = expression1.expId,
                            targetExpId = expression2.expId
                        )
                        val poolId: Long = poolRepo.addEntity(mWordPool)
                        val result: WordPool = poolRepo.readEntity(poolId)
                        Log.d(TAG, "POOL : $result")

                    } else {
                        break
                    }
                }

                withContext(Dispatchers.Main){
                    setIsAddWord(true)
                }

            } catch (e: Exception){
                withContext(Dispatchers.Main){
                    setIsAddWord(false)
                }
            } finally {
                inputStream?.close()
                reader?.close()
            }
        }
    }
}