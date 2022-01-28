package com.androiddevs.roots

import com.androiddevs.data.checkPasswordForEmail
import com.androiddevs.data.requests.AccountRequest
import com.androiddevs.data.responses.SimpleResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.loginRoute() {
    route("/login") {
        post {
            val request = try {
                call.receive<AccountRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val isPasswordCheck = checkPasswordForEmail(request.email, request.password)
            if(isPasswordCheck) {
                call.respond(OK, SimpleResponse(true, "You are now logged in!"))
            } else {
                call.respond(OK, SimpleResponse(false, "The email or password is incorrect"))
            }
        }
    }
}
