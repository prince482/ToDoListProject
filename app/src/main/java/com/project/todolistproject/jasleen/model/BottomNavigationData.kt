package com.project.todolistproject.jasleen.model

/**
 * Created by Jasleen Kaur on 26-02-2021.
 */
class BottomNavigationData {

    var textFont:String=""
    var color:Int=0
    var textID:Int=0
    var colorID:Int=0
    var changeID:Int=0


    constructor(changeId:Int,textId:Int,textFont: String) {
        this.textFont = textFont
        this.textID=textId
        this.changeID=changeId
    }

    constructor(changeId:Int,colorID:Int,color: Int) {
        this.color = color
        this.colorID=colorID
        this.changeID=changeId
    }


}