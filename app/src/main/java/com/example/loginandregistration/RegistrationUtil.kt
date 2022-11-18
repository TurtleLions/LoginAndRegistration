package com.example.loginandregistration

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
            if(password.isEmpty()||confirmPassword.isEmpty()||password.length<8 || !password.contains("[0-9]".toRegex()) || !password.contains("[A-Z]".toRegex()) || password!==confirmPassword){
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
            if(email.isEmpty()||!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                return false
            }
            for(x in existingEmails){
                if(email===x){
                    return false
                }
            }
            return true
        }
    }
}