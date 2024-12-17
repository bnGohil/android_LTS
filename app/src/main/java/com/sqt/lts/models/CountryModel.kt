package com.example.lts.dummy.models

data class CountryModel(val countryName:String?=null)

 object CountryListObject{

     val countryList = arrayListOf<CountryModel>(
         CountryModel(countryName = "Australia"),
         CountryModel(countryName = "Belgium"),
         CountryModel(countryName = "Canada"),
         CountryModel(countryName = "Croatia"),
         CountryModel(countryName = "Cyprus"),
         CountryModel(countryName = "Czech Republic"),
         CountryModel(countryName = "Denmark"),
         CountryModel(countryName = "Estonia"),
         CountryModel(countryName = "Finland"),
         CountryModel(countryName = "France"),
         CountryModel(countryName = "Germany"),
         CountryModel(countryName = "Gibraltar"),
     )

     val countryCode = arrayListOf<CountryModel>(
         CountryModel(countryName = "+91"),
         CountryModel(countryName = "+91"),
         CountryModel(countryName = "+91"),
         CountryModel(countryName = "+91"),
         CountryModel(countryName = "+91"),
         CountryModel(countryName = "+91"),
         CountryModel(countryName = "+91"),
         CountryModel(countryName = "+91"),
         CountryModel(countryName = "+91"),
         CountryModel(countryName = "+91"),
         CountryModel(countryName = "+91"),
         CountryModel(countryName = "+91"),
         CountryModel(countryName = "+91"),
         CountryModel(countryName = "+91"),
         CountryModel(countryName = "+91"),
         CountryModel(countryName = "+91"),
         CountryModel(countryName = "+91"),
         CountryModel(countryName = "+91"),
     )

 }
