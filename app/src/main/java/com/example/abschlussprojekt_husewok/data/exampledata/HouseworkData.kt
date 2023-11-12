package com.example.abschlussprojekt_husewok.data.exampledata

import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.model.Housework

/**
 * Object to store the default housework data.
 */
object HouseworkData {
    /**
     * List of default housework tasks.
     */
    val houseworkList = listOf(
        Housework(
            image = R.drawable.img_clean_floors,
            title = "Clean Floors",
            task1 = "Pick everything up from floors",
            task2 = "Vacuum floors",
            task3 = "Mop floors",
            isLiked = true,
            lockDurationDays = 5,
            lockExpirationDate = "",
            default = true,
            id = "default_clean_floors"
        ),
        Housework(
            image = R.drawable.img_clean_bathroom,
            title = "Clean Bathroom",
            task1 = "Clean toilet, sink, and shower",
            task2 = "Scrub bathtub and tiles",
            task3 = "Wipe down countertops and mirrors",
            isLiked = false,
            lockDurationDays = 7,
            lockExpirationDate = "",
            default = true,
            id = "default_clean_bathroom"
        ),
        Housework(
            image = R.drawable.img_tidy_livingroom,
            title = "Tidy Livingroom",
            task1 = "Dust furniture",
            task2 = "Vacuum or sweep floors",
            task3 = "Wipe down surfaces",
            isLiked = true,
            lockDurationDays = 1,
            lockExpirationDate = "",
            default = true,
            id = "default_tidy_livingroom"
        ),
        Housework(
            image = R.drawable.img_clean_oven,
            title = "Clean Oven",
            task1 = "Remove racks and clean separately",
            task2 = "Apply oven cleaner and let sit",
            task3 = "Wipe down interior",
            isLiked = false,
            lockDurationDays = 14,
            lockExpirationDate = "",
            default = true,
            id = "default_clean_oven"
        ),
        Housework(
            image = R.drawable.img_clean_refrigerator,
            title = "Clean Refrigerator",
            task1 = "Empty contents and remove shelves",
            task2 = "Wipe down interior",
            task3 = "Clean door seals",
            isLiked = true,
            lockDurationDays = 7,
            lockExpirationDate = "",
            default = true,
            id = "default_clean_refrigerator"
        ),
        Housework(
            image = R.drawable.img_organize_paperwork,
            title = "Organize Paperwork",
            task1 = "Sort and organize documents",
            task2 = "File important papers",
            task3 = "Shred or discard unnecessary documents",
            isLiked = true,
            lockDurationDays = 1,
            lockExpirationDate = "",
            default = true,
            id = "default_organize_paperwork"
        ),
        Housework(
            image = R.drawable.img_empty_trash,
            title = "Empty Trash",
            task1 = "Gather trash from all rooms",
            task2 = "Dispose of trash bags",
            task3 = "Clean trash cans if necessary",
            isLiked = true,
            lockDurationDays = 2,
            lockExpirationDate = "",
            default = true,
            id = "default_empty_trash"
        ),
        Housework(
            image = R.drawable.img_tidy_bedroom,
            title = "Tidy Bedroom",
            task1 = "Make the bed",
            task2 = "Put away clothes and belongings",
            task3 = "Dust surfaces",
            isLiked = true,
            lockDurationDays = 1,
            lockExpirationDate = "",
            default = true,
            id = "default_tidy_bedroom"
        ),
        Housework(
            image = R.drawable.img_tidy_kitchen,
            title = "Tidy Kitchen",
            task1 = "Wipe down countertops",
            task2 = "Put away dishes",
            task3 = "Organize pantry and cabinets",
            isLiked = true,
            lockDurationDays = 1,
            lockExpirationDate = "",
            default = true,
            id = "default_tidy_kitchen"
        ),
        Housework(
            image = R.drawable.img_wash_clothes,
            title = "Wash Clothes",
            task1 = "Sort laundry by color",
            task2 = "Load washing machine",
            task3 = "Dry and fold clothes",
            isLiked = false,
            lockDurationDays = 4,
            lockExpirationDate = "",
            default = true,
            id = "default_wash_clothes"
        ),
        Housework(
            image = R.drawable.img_wash_dishes,
            title = "Wash Dishes",
            task1 = "Scrape off food residue",
            task2 = "Wash dishes with soap and water",
            task3 = "Dry and put away",
            isLiked = false,
            lockDurationDays = 1,
            lockExpirationDate = "",
            default = true,
            id = "default_wash_dishes"
        )
    )
}