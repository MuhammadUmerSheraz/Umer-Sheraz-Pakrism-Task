package umer.task.pakrism.utils

import android.content.Context
import retrofit2.Response


abstract class SafeApiRequest(context: Context) {
    val SERVICE_UNAVAILABLE = "Service currently unavailable"


    suspend fun <T : Any> apiCallRepoParse(
        call: suspend () -> Response<T>,
        successResponse: ((response: T) -> Unit)? = null,
        errorResponse: ((response: Exception) -> Unit)? = null
    ): UseCaseResult<T> {
        return try {
            val result = apiResponseParse(call)
            successResponse?.let { it(result) }
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            errorResponse?.let { it(ex) }
            return UseCaseResult.Error(ex)
        }
    }

    suspend fun <T : Any> apiResponseParse(call: suspend () -> Response<T>): T {
        val response: Response<T>

        /** Checking parsing errors start */
        try {
            response = call.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(SERVICE_UNAVAILABLE)
        }
        /** Checking parsing errors end */

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw ApiException(SERVICE_UNAVAILABLE)


        }
    }

}