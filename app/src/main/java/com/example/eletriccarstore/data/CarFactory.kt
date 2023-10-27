package com.example.eletriccarstore.data

import com.example.eletriccarstore.domain.Car

object CarFactory {
    val car1  = Car(1,  "R$ 100,000", "2000mAh", "300cv", "220V", "url1")
    val car2  = Car(2,  "R$ 120,000", "2500mAh", "350cv", "240V", "url2")
    val car3  = Car(3,  "R$ 90,000",  "1800mAh", "280cv", "200V", "url3")
    val car4  = Car(4,  "R$ 110,000", "2100mAh", "320cv", "230V", "url4")
    val car5  = Car(5,  "R$ 95,000",  "1900mAh", "290cv", "210V", "url5")
    val car6  = Car(6,  "R$ 130,000", "2600mAh", "380cv", "250V", "url6")
    val car7  = Car(7,  "R$ 85,000",  "1700mAh", "270cv", "190V", "url7")
    val car8  = Car(8,  "R$ 115,000", "2300mAh", "340cv", "220V", "url8")
    val car9  = Car(9,  "R$ 105,000", "2100mAh", "310cv", "230V", "url9")
    val car10 = Car(10, "R$ 125,000", "2400mAh", "360cv", "240V", "url10")

    val carList = arrayListOf<Car>(car1, car2, car3, car4, car5, car6, car7, car8, car9, car10)
}