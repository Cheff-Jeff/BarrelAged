package com.example.barrelaged.modals.dayModals

class sortDayOverView {
    private var days: ArrayList<dayOverView> = ArrayList<dayOverView>()

    fun sort(beers: List<beerDTO>): List<dayOverView>{
        outerloop@ for (beer in  beers){
            if(days.isEmpty()){
                days.add(dayOverView(beer.beerDate, 1))
                continue
            }
            for (day in days){
                if(beer.beerDate == day.date){
                    day.amount ++
                    continue@outerloop
                }
            }
            days.add(dayOverView(beer.beerDate, 1))
        }

        return days
    }
}