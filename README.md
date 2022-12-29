# meins_core
good api/lib plugin (aswell as being needed for many of my plugins)
## Documentation

### Update Checker
<pre>
  Checks updates for plugins on github
  
  Prequisits:
    version.txt in github repo (same place as readme.md)
    version.txt should have just 1 line with version 
    format (eg.: "1.0","v1.1","v1.2-beta") doesn't matter thou has to be the same in [STRING_version_of_this_plugin]
  
  How to use it:
    now put this in your plugin main onEnable:
      new UpdateChecker().check("[STRING_version_of_this_plugin]", "[STRING_github_profile]", "[STRING_repo_name]");
    replace [STRING_version_of_this_plugin], [STRING_github_profile] and [STRING_repo_name] with well.. with whatever is in the []
    finished now it will send a message in console everytime onEnable gets triggered telling you if it needs an update or not (colored as for convinience)
</pre>

### Log
<pre>
  Logs a message in console
  
  How to use it:
    new MCore().log([STRING_message]);
    
  will be updated with .info; .error; and .success soon™
</pre>

### Item Builder
<pre>
  Lets you build an itemstack
  
  How to use it:
    new ItemBuilder([MATERIAL_material], [INT_amount]).build() //returns a ItemStack with said properties
    (.build() has to be at end or it will just give you the ItemBuilder)
    
    you can also use: 
      .setName([STRING_name]) //sets name of itemstack to [STRING_name]
      .setLore([STRING_ARRAY_lore]) //changes entire lore to [STRING_ARRAY_lore]
      .setLoreAt([INT_index], [STRING_lore]) //changes lore at [INT_index] in array to [STRING_lore]
      .addLore([STRING_lore]) //adds lore [STRING_lore] at end of current lore array
      .addEnchantment([ENCHANTMENT_enchantment], [INT_level]) //enchants item with [ENCHANTMENT_enchantment] at level [INT_level]
      .addMaxEnchantment([ENCHANTMENT_enchantment]) //enchants item with [ENCHANTMENT_enchantment] at max level
</pre>

### GUI Builder
<pre>
 WIP and Documentation WIP
</pre>

### QUESTIONS?
<pre>
if(any_questions()) {
  try {
    join_discord("https://discord.gg/vytuPSmJ4c");
  } catch (Exeption e) {
    e.printStackTrace();
  }
}
</pre>
