package com.example.phinconattendance.screen.login

class LoginUtils {

    fun loginFormatValidation(email: String, password: String): Int {
        if (email.trim().isNotEmpty()) {
            if (email.length > 5) {
                if (email.contains("@")) {
                    if(password.length<=8){
                        return if (password.trim().isNotEmpty()) {
                            1
                        } else {
                            6
                        }
                    }else{
                        return 5
                    }
                } else {
                    return 4
                }
            } else {
                return 3
            }
        } else {
            return 2
        }
    }
}