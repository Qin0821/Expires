package com.simpure.expires.view.popup

import com.simpure.expires.model.Inventory

interface InventoryClickListener {
    fun onClick(inventory: Inventory)
}