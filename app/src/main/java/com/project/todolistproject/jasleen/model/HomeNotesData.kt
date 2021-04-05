package com.project.todolistproject.jasleen.model

/**
 * Created by Jasleen Kaur on 23-02-2021.
 */
class HomeNotesData {
    var title: String = ""
    var body: String = ""
    var date: String = ""
    var id: String = ""
    var titleClr: String=""
    var txtClr: String = ""

    constructor()

    constructor(title: String, body: String, date: String, id: String, titleClr: String, txtClr: String) {
        this.title = title
        this.body = body
        this.date = date
        this.id = id
        this.titleClr = titleClr
        this.txtClr = txtClr
    }

    constructor(title: String, body: String, date: String, id: String) {
        this.title = title
        this.body = body
        this.date = date
        this.id = id
    }

}