package com.example.abschlussprojekt_husewok.data.exampledata

import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.model.Housework

object HouseworkData {
    // Create a list of Housework objects
    val houseworkList = listOf(
        Housework(
            image = R.drawable.clean_floors,
            title = "Clean Floors",
            description = "Pick everything up from floors\nVacuum floors\nMop floors",
            lockDurationDays = 7
        ),
        Housework(
            image = R.drawable.clean_bathroom,
            title = "Clean Bathroom",
            description = "Clean toilet, sink, and shower\nScrub bathtub and tiles\nWipe down countertops and mirrors",
            lockDurationDays = 7
        ),
        Housework(
            image = R.drawable.clean_livingroom,
            title = "Clean Living Room",
            description = "Dust furniture\nVacuum or sweep floors\nWipe down surfaces",
            lockDurationDays = 1
        ),
        Housework(
            image = R.drawable.clean_oven,
            title = "Clean Oven",
            description = "Remove racks and clean separately\nApply oven cleaner and let sit\nWipe down interior",
            lockDurationDays = 14
        ),
        Housework(
            image = R.drawable.clean_refrigerator,
            title = "Clean Refrigerator",
            description = "Empty contents and remove shelves\nWipe down interior\nClean door seals",
            lockDurationDays = 14
        ),
        Housework(
            image = R.drawable.paperwork,
            title = "Organize Paperwork",
            description = "Sort and organize documents\nFile important papers\nShred or discard unnecessary documents",
            lockDurationDays = 1
        ),
        Housework(
            image = R.drawable.empty_trash,
            title = "Empty Trash",
            description = "Gather trash from all rooms\nDispose of trash bags\nClean trash cans if necessary",
            lockDurationDays = 2
        ),
        Housework(
            image = R.drawable.tidy_bedroom,
            title = "Tidy Bedroom",
            description = "Make the bed\nPut away clothes and belongings\nDust surfaces",
            lockDurationDays = 1
        ),
        Housework(
            image = R.drawable.tidy_kitchen,
            title = "Tidy Kitchen",
            description = "Wipe down countertops\nPut away dishes\nOrganize pantry and cabinets",
            lockDurationDays = 1
        ),
        Housework(
            image = R.drawable.wash_clothes,
            title = "Wash Clothes",
            description = "Sort laundry by color\nLoad washing machine\nDry and fold clothes",
            lockDurationDays = 4
        ),
        Housework(
            image = R.drawable.wash_dishes,
            title = "Wash Dishes",
            description = "Scrape off food residue\nWash dishes with soap and water\nDry and put away",
            lockDurationDays = 1
        )
    )
}