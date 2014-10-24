package com.toyz.warpcost.Utils;

import com.toyz.warpcost.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Travis on 10/24/2014.
 */
public class Inventory {
    private Player _player = null;
    private Hashtable<Integer, ItemStack> _items = null;
    private org.bukkit.inventory.Inventory _Inventory = null;
    private int _size = 0;
    private int _rowLength = 54;
    public Inventory(Player p, Hashtable<Integer, ItemStack> items, Boolean useRow, String title){
        _player = p;
        _items = items;

        _size = Main.getPlugin().getConfig().getInt("guisize");
        if(useRow)
            _rowLength = _size * 9;

        _Inventory = Bukkit.createInventory(null, _rowLength, title);

        Build();
    }

    public org.bukkit.inventory.Inventory getInventory(){
        return this._Inventory;
    }

    private void Build(){
        int i = 0;
        Iterator<Map.Entry<Integer, ItemStack>> it = _items.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry<Integer, ItemStack> entry = it.next();
            if(entry.getKey() < _rowLength){
                _Inventory.setItem(entry.getKey(), entry.getValue());
                i++;
            }
            //MyTokens.console.sendMessage(MessageHelper.Format(null, "&Loaded item @ index " + entry.getKey()));
        }
    }

    public void Open(){
        _player.openInventory(_Inventory);
    }
}
