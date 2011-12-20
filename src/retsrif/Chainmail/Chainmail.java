package retsrif.Chainmail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapedRecipe;
import org.getspout.spoutapi.material.CustomItem;
import org.getspout.spoutapi.material.MaterialData;

public class Chainmail extends JavaPlugin {
	public static CustomItem ci;

	@Override
	public void onDisable() {
		System.out.println("[Chainmail] disabled.");
	}

	@Override
	public void onEnable() {
		extractFile("Chains.png", true);
		ci = new Chain(this, "Chain", "/plugins/Chainmail/Chains.png");
		setupRecipes();
		System.out.println("[Chainmail] enabled.");
	}
	
	public void setupRecipes() {
		ItemStack chain = new SpoutItemStack(ci, 8);
		SpoutShapedRecipe chainRecipe = new SpoutShapedRecipe(chain);
		chainRecipe.shape(" B ", "B B", " B ");
		chainRecipe.setIngredient('B', MaterialData.ironIngot);
		SpoutManager.getMaterialManager().registerSpoutRecipe(chainRecipe);
		
		ItemStack chainHelmet = new SpoutItemStack(MaterialData.chainHelmet ,1);
		SpoutShapedRecipe helmetRecipe = new SpoutShapedRecipe(chainHelmet);
		helmetRecipe.shape("BBB", "B B");
		helmetRecipe.setIngredient('B', ci);
		SpoutManager.getMaterialManager().registerSpoutRecipe(helmetRecipe);
		
		ItemStack chainBody = new SpoutItemStack(MaterialData.chainChestplate ,1);
		SpoutShapedRecipe bodyRecipe = new SpoutShapedRecipe(chainBody);
		bodyRecipe.shape("B B", "BBB", "BBB");
		bodyRecipe.setIngredient('B', ci);
		SpoutManager.getMaterialManager().registerSpoutRecipe(bodyRecipe);
		
		ItemStack chainLegs = new SpoutItemStack(MaterialData.chainLeggings ,1);
		SpoutShapedRecipe legsRecipe = new SpoutShapedRecipe(chainLegs);
		legsRecipe.shape("BBB", "B B", "B B");
		legsRecipe.setIngredient('B', ci);
		SpoutManager.getMaterialManager().registerSpoutRecipe(legsRecipe);
		
		ItemStack chainBoots = new SpoutItemStack(MaterialData.chainBoots ,1);
		SpoutShapedRecipe bootsRecipe = new SpoutShapedRecipe(chainBoots);
		bootsRecipe.shape("B B", "B B");
		bootsRecipe.setIngredient('B', ci);
		SpoutManager.getMaterialManager().registerSpoutRecipe(bootsRecipe);
	}
	
	//Code taken from Pamelloes' More Furnaces port
	public boolean extractFile(String regex, boolean cache) {
		boolean found = false;
		try {
			JarFile jar = new JarFile(getFile());
			for (Enumeration<JarEntry> entries = jar.entries(); entries.hasMoreElements();) {
				JarEntry entry = (JarEntry) entries.nextElement();
				String name = entry.getName();
				if (name.matches(regex)) {
					if (!getDataFolder().exists()) {
						getDataFolder().mkdir();
					}
					try {
						File file = new File(getDataFolder(), name);
						if (!file.exists()) {
							InputStream is = jar.getInputStream(entry);
							FileOutputStream fos = new FileOutputStream(file);
							while (is.available() > 0) {
								fos.write(is.read());
							}
							fos.close();
							is.close();
							found = true;
						}
						if (cache && name.matches(".*\\.(txt|yml|xml|png|jpg|ogg|midi|wav|zip)$")) {
							SpoutManager.getFileManager().addToPreLoginCache(this, file);
						}
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception e) {
		}
		return found;
	}

}
