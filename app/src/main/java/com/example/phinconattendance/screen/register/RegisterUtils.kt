package com.example.phinconattendance.screen.register

class RegisterUtils {
    fun registerFormatValidation(email: String, password: String, confirm: String): Int {
        if (email.trim().isNotEmpty()) {
            if (email.length > 5) {
                if (email.contains("@")) {
                    if (password.trim().isNotEmpty() && confirm.trim().isNotEmpty()) {
                        if (password == confirm) {
                            if (password.length >= 8 && confirm.length >= 8) {
                                return 1
                            } else {
                                return 7
                            }
                        } else {
                            return 6
                        }
                    } else {
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