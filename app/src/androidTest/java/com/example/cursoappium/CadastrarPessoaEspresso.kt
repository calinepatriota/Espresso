package com.example.cursoappium

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import junit.framework.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CadastrarPessoaEspresso {

    val toastMessageSuccess = "Cadastro realizado com sucesso"
    val toastMessageDuplicated = "Email já cadastrado"
    val device: UiDevice

    get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    // definição da activity a ser testada
    @get:Rule var activityScenarioRule = ActivityScenarioRule(RegisterUsers::class.java)

    private fun getRandomString(length: Int) : String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return List(length) { charset.random() }
            .joinToString("")
    }

    @Test
    fun testCadastrarPessoaValida(){
        val randomName = getRandomString(10)
        Espresso.onView(ViewMatchers.withId(R.id.TextInputNome))
            .perform(ViewActions.typeText(randomName), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.TextInputEmail))
            .perform(ViewActions.typeText("$randomName@test.com"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.radioButton_homem)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.spinner_estados)).perform(ViewActions.click())
        Espresso.onView(withText("Bahia (BA)")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.BotaoCadastrar)).perform(ViewActions.click())
        assertTrue(device.hasObject(By.text(toastMessageSuccess)))
    }


    @Test
    fun testCadastrarContaDuplicada(){
        Espresso.onView(ViewMatchers.withId(R.id.TextInputNome))
            .perform(ViewActions.typeText("Francisca"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.TextInputEmail))
            .perform(ViewActions.typeText("francisca@test.com"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.radioButton_mulher)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.spinner_estados)).perform(ViewActions.click())
        Espresso.onView(withText("Amazonas (AM)")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.BotaoCadastrar)).perform(ViewActions.click())


        Espresso.onView(withId(R.id.TextInputNome))
            .perform(typeText("Francisca"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.TextInputEmail))
            .perform(typeText("francisca@test.com"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.radioButton_mulher)).perform(click())
        Espresso.onView(withId(R.id.spinner_estados)).perform(click())
        Espresso.onView(withText("Amazonas (AM)")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.BotaoCadastrar)).perform(click())
        assertTrue(device.hasObject(By.text(toastMessageDuplicated)))
    }

    @Test
    fun testCadastrarComEmailVazio(){
        val randomName = getRandomString(10)
        Espresso.onView(ViewMatchers.withId(R.id.TextInputNome))
            .perform(ViewActions.typeText(randomName), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.radioButton_mulher)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.spinner_estados)).perform(ViewActions.click())
        Espresso.onView(withText("Ceará (CE)")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.BotaoCadastrar)).perform(ViewActions.click())
        assertTrue(!device.hasObject(By.text(toastMessageSuccess)))
        assertTrue(device.hasObject(By.text("Insira um email válido")))
    }

    @Test
    fun testBotaoVoltar(){
        Espresso.onView(withId(R.id.BotaoVoltar))
    }

    @Test
    fun testCadastrarEmailInvalido(){
        val randomName = getRandomString(10)
        Espresso.onView(ViewMatchers.withId(R.id.TextInputNome))
            .perform(ViewActions.typeText(randomName), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.TextInputEmail))
            .perform(ViewActions.typeText("teste"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.radioButton_mulher)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.spinner_estados)).perform(ViewActions.click())
        Espresso.onView(withText("Goiás (GO)")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.BotaoCadastrar)).perform(ViewActions.click())
        assertTrue(device.hasObject(By.text("Insira um email válido")))
        assertTrue(!device.hasObject(By.text(toastMessageSuccess)))
    }

    @Test
    fun testCadastrarComNomeVazio(){
        val randomName = getRandomString(10)
        Espresso.onView(ViewMatchers.withId(R.id.TextInputEmail))
            .perform(ViewActions.typeText("$randomName@test.com"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.radioButton_mulher)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.spinner_estados)).perform(ViewActions.click())
        Espresso.onView(withText("Alagoas (AL)")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.BotaoCadastrar)).perform(ViewActions.click())
        assertTrue(!device.hasObject(By.text(toastMessageSuccess)))
        assertTrue(device.hasObject(By.text("Insira o nome completo")))
    }
}