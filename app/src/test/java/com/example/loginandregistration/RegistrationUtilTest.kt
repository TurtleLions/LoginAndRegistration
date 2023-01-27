package com.example.loginandregistration

import com.example.loginandregistration.RegistrationUtil.RegistrationUtil.validateUsername
import com.example.loginandregistration.RegistrationUtil.RegistrationUtil.validatePassword
import com.example.loginandregistration.RegistrationUtil.RegistrationUtil.validateName
import com.example.loginandregistration.RegistrationUtil.RegistrationUtil.validateEmail
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest {
    @Test
    fun validatePassword_emptyPassword_isFalse(){
        val actual = validatePassword("","")
        assertThat(actual).isFalse()
    }

    @Test
    fun validatePassword_passwordsDontMatch_isFalse(){
        val actual = validatePassword("Jakdljasd2", "Laaskjdm3")
        assertThat(actual).isFalse()
    }

    @Test
    fun validatePassword_passwordTooShort_isFalse(){
        val actual = validatePassword("Yes1", "Yes1")
        assertThat(actual).isFalse()
    }

    @Test
    fun validatePassword_noDigit_isFalse(){
        val actual = validatePassword("GoodMorning", "GoodMorning")
        assertThat(actual).isFalse()
    }

    @Test
    fun validatePassword_noCaps_isFalse(){
        val actual = validatePassword("asldfjlkj2", "asldfjlkj2")
        assertThat(actual).isFalse()
    }

    @Test
    fun validatePassword_success_isTrue(){
        val actual = validatePassword("Good1Password", "Good1Password")
        assertThat(actual).isTrue()
    }

    @Test
    fun validateUsername_tooShort_isFalse(){
        val actual = validateUsername("no")
        assertThat(actual).isFalse()
    }

    @Test
    fun valiadateUsername_alreadyExists_isFalse(){
        val actual = validateUsername("bob")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateUsername_success_isTrue(){
        val actual = validateUsername("nom")
        assertThat(actual).isTrue()
    }

    @Test
    fun validateName_empty_isFalse(){
        val actual = validateName("")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateName_success_isTrue(){
        val actual = validateName("Jack")
        assertThat(actual).isTrue()
    }

    @Test
    fun validateEmail_notAEmail_isFalse(){
        val actual = validateEmail("bob")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateEmail_alreadyUsed_isFalse(){
        val actual = validateEmail("cosmicF@gmail.com")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateEmail_success_isTrue(){
        val actual = validateEmail("yes@gmail.com")
        assertThat(actual).isTrue()
    }
}