package com.example.loginandregistration

import java.util.regex.Pattern

class RegistrationUtil {

    object RegistrationUtil {

        var existingUsers = mutableListOf<String>("cosmicF", "cosmicY", "bob", "alice")
        var existingEmails = mutableListOf<String>("cosmicF@gmail.com", "cosmicY@gmail.com", "bob@gmail.com", "alice@gmail.com")


        fun validateUsername(username: String) : Boolean {
            if(username.isEmpty()||username.length<3){
                return false
            }
            for(x in existingUsers){
                if(username===x){
                    return false
                }
            }
            return true
        }


        fun validatePassword(password : String, confirmPassword: String) : Boolean {
            if(password.isEmpty()||confirmPassword.isEmpty()||password.length<8 || !password.contains("[0-9]".toRegex()) || !password.contains("[A-Z]".toRegex()) || !password.equals(confirmPassword)){
                return false
            }
            return true
        }


        fun validateName(name: String) : Boolean {
            if(name.isEmpty()){
                return false
            }
            return true
        }


        fun validateEmail(email: String) : Boolean {
            val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
            )

            if(email.isEmpty()||!EMAIL_ADDRESS_PATTERN.matcher(email).matches()){
                return false
            }
            for(x in existingEmails) {
                if (email === x) {
                    return false
                }
            }
            return true
        }
    }
}