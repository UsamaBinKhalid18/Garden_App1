package com.example.gardenapp.datahandling

import android.content.Context
import android.os.Looper
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import java.io.FileInputStream

@RunWith(AndroidJUnit4::class)
internal class DataTest{

    @Test
    fun validJsonString_returnsListObject(){
        val jsonString="""
            [
              {
                "plantId": "malus-pumila",
                "name": "Apple",
                "description": "An apple is a sweet, edible fruit produced by an apple tree (Malus pumila). Apple trees are cultivated worldwide, and are the most widely grown species in the genus Malus. The tree originated in Central Asia, where its wild ancestor, Malus sieversii, is still found today. Apples have been grown for thousands of years in Asia and Europe, and were brought to North America by European colonists. Apples have religious and mythological significance in many cultures, including Norse, Greek and European Christian traditions.<br><br>Apple trees are large if grown from seed. Generally apple cultivars are propagated by grafting onto rootstocks, which control the size of the resulting tree. There are more than 7,500 known cultivars of apples, resulting in a range of desired characteristics. Different cultivars are bred for various tastes and uses, including cooking, eating raw and cider production. Trees and fruit are prone to a number of fungal, bacterial and pest problems, which can be controlled by a number of organic and non-organic means. In 2010, the fruit's genome was sequenced as part of research on disease control and selective breeding in apple production.<br><br>Worldwide production of apples in 2014 was 84.6 million tonnes, with China accounting for 48% of the total.<br><br>(From <a href=\"https://en.wikipedia.org/wiki/Apple\">Wikipedia</a>)",
                "growZoneNumber": 3,
                "wateringInterval": 30,
                "imageUrl": "https://upload.wikimedia.org/wikipedia/commons/5/55/Apple_orchard_in_Tasmania.jpg"
              },
              {
                "plantId": "beta-vulgaris",
                "name": "Beet",
                "description": "The beetroot is the taproot portion of the beet plant, usually known in North America as the beet and also known as the table beet, garden beet, red beet, or golden beet. It is one of several of the cultivated varieties of Beta vulgaris grown for their edible taproots and their leaves (called beet greens). These varieties have been classified as B. vulgaris subsp. vulgaris Conditiva Group.<br><br>Other than as a food, beets have use as a food colouring and as a medicinal plant. Many beet products are made from other Beta vulgaris varieties, particularly sugar beet.<br><br>(From <a href=\"https://en.wikipedia.org/wiki/Beetroot\">Wikipedia</a>)",
                "growZoneNumber": 2,
                "wateringInterval": 7,
                "imageUrl": "https://upload.wikimedia.org/wikipedia/commons/2/29/Beetroot_jm26647.jpg"
              }]
                """.trimIndent()
        val result =Data.plantListFromDataString(jsonString,ApplicationProvider.getApplicationContext())
        assertThat(result).isInstanceOf(List::class.java)
        assertThat(result[0]).isInstanceOf(Plant::class.java)
    }

    @Test
    fun inValidJsonString_returnsDefaultListObject(){
        Looper.prepare()

        val jsonString=""
        val result =Data.plantListFromDataString(jsonString,ApplicationProvider.getApplicationContext())
        assertThat(result).isInstanceOf(List::class.java)
        assertThat(result[0]).isInstanceOf(Plant::class.java)
        assertThat(result.count()).isEqualTo(17)
    }

    @Test
    fun saveDataInFile(){
        Data.savePlantsList(ApplicationProvider.getApplicationContext())
        val dataFile=ApplicationProvider.getApplicationContext<Context>().filesDir.path+"/plants.json"

        val string= FileInputStream(dataFile).reader().readText()
        val result =Data.plantListFromDataString(string,ApplicationProvider.getApplicationContext())

        assertThat(result).isEqualTo(Data.plantsList)
    }
}