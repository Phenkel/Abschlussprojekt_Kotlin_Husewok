package com.example.abschlussprojekt_husewok.data.exampledata

import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.model.Housework

object HouseworkData {
    // Create a list of Housework objects
    val houseworkList = listOf(
        Housework(
            image = R.drawable.img_clean_floors,
            title = "Clean Floors",
            task1 = "Pick everything up from floors",
            task2 = "Vacuum floors",
            task3 = "Mop floors",
            lockDurationDays = 7
        ),
        Housework(
            image = R.drawable.img_clean_bathroom,
            title = "Clean Bathroom",
            task1 = "Clean toilet, sink, and shower",
            task2 = "Scrub bathtub and tiles",
            task3 = "Wipe down countertops and mirrors",
            isLiked = false,
            lockDurationDays = 7
        ),
        Housework(
            image = R.drawable.img_tidy_livingroom,
            title = "Tidy Livingroom",
            task1 = "Dust furniture",
            task2 = "Vacuum or sweep floors",
            task3 = "Wipe down surfaces",
            lockDurationDays = 1
        ),
        Housework(
            image = R.drawable.img_clean_oven,
            title = "Clean Oven",
            task1 = "Remove racks and clean separately",
            task2 = "Apply oven cleaner and let sit",
            task3 = "Wipe down interior",
            isLiked = false,
            lockDurationDays = 14
        ),
        Housework(
            image = R.drawable.img_clean_refrigerator,
            title = "Clean Refrigerator",
            task1 = "Empty contents and remove shelves",
            task2 = "Wipe down interior",
            task3 = "Clean door seals",
            lockDurationDays = 14
        ),
        Housework(
            image = R.drawable.img_organize_paperwork,
            title = "Organize Paperwork",
            task1 = "Sort and organize documents",
            task2 = "File important papers",
            task3 = "Shred or discard unnecessary documents",
            lockDurationDays = 1
        ),
        Housework(
            image = R.drawable.img_empty_trash,
            title = "Empty Trash",
            task1 = "Gather trash from all rooms",
            task2 = "Dispose of trash bags",
            task3 = "Clean trash cans if necessary",
            lockDurationDays = 2
        ),
        Housework(
            image = R.drawable.img_tidy_bedroom,
            title = "Tidy Bedroom",
            task1 = "Make the bed",
            task2 = "Put away clothes and belongings",
            task3 = "Dust surfaces",
            lockDurationDays = 1
        ),
        Housework(
            image = R.drawable.img_tidy_kitchen,
            title = "Tidy Kitchen",
            task1 = "Wipe down countertops",
            task2 = "Put away dishes",
            task3 = "Organize pantry and cabinets",
            lockDurationDays = 1
        ),
        Housework(
            image = R.drawable.img_wash_clothes,
            title = "Wash Clothes",
            task1 = "Sort laundry by color",
            task2 = "Load washing machine",
            task3 = "Dry and fold clothes",
            lockDurationDays = 4
        ),
        Housework(
            image = R.drawable.img_wash_dishes,
            title = "Wash Dishes",
            task1 = "Scrape off food residue",
            task2 = "Wash dishes with soap and water",
            task3 = "Dry and put away",
            isLiked = false,
            lockDurationDays = 1
        )
    )
}