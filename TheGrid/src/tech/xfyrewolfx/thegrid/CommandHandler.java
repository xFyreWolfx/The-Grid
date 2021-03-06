package tech.xfyrewolfx.thegrid;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tech.xfyrewolfx.thegrid.listeners.AddOutlet;
import tech.xfyrewolfx.thegrid.listeners.AddSystem;
import tech.xfyrewolfx.thegrid.listeners.RemSystem;

public class CommandHandler implements CommandExecutor{
	
	TheGrid plugin;
	public CommandHandler(TheGrid c){
		plugin=c;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("thegrid")){
			if(sender.hasPermission("thegrid.admin")){
				if(args.length==0){
					sender.sendMessage("�a===�8===�a===�8===�a===�8===�a===�8===�a===�8===");
					sender.sendMessage("�7/thegrid addSystem <name> <level>");
					sender.sendMessage("�7/thegrid remSystem");
					sender.sendMessage("�7/thegrid addOutlet");
					sender.sendMessage("�7/thegrid addBitcoin <onlineplayer> <amount>");
					sender.sendMessage("�7/thegrid setSpawn");
					sender.sendMessage("�7/thegrid setTutorial");
					sender.sendMessage("�7/thegrid reload");
					sender.sendMessage("�e/gridspawn �7- Used by players to return to Spawn");
					sender.sendMessage("�a===�8===�a===�8===�a===�8===�a===�8===�a===�8===");
				}else{
					if(args[0].equalsIgnoreCase("addSystem")){
						if(args.length==3){
							if(this.isInteger(args[2])){
								Bukkit.getPluginManager().registerEvents(new AddSystem(plugin, (Player)sender, args[1], Integer.parseInt(args[2])), plugin);
								sender.sendMessage("Click the new system block");
							}else{
								sender.sendMessage(plugin.getMessages().wrongCommand());
							}
						}else{
							sender.sendMessage(plugin.getMessages().wrongCommand());
						}
					}else{
						if(args[0].equalsIgnoreCase("addOutlet")){
							Bukkit.getPluginManager().registerEvents(new AddOutlet(plugin, (Player)sender), plugin);
							sender.sendMessage("Click the TRIPWIRE HOOK to add an outlet");
						}else{
							if(args[0].equalsIgnoreCase("setSpawn")){
								Player p = (Player)sender;
								plugin.getUserConfig().setSpawnLocation(p.getLocation());
								p.sendMessage("Spawn location set!");
							}else{
								if(args[0].equalsIgnoreCase("setTutorial")){
									Player p = (Player)sender;
									plugin.getUserConfig().setTutorialLocation(p.getLocation());
									p.sendMessage("Tutorial location set!");
								}else{
									if(args[0].equalsIgnoreCase("reload")){
										plugin.getUserConfig().loadValues();
										plugin.getMessages().reloadMessages();
										plugin.getMessages().loadValues();
										plugin.getItems().reloadItems();
										sender.sendMessage("Config files reloaded!");
									}else{
										if(args[0].equalsIgnoreCase("addBitcoin")){
											if(args.length==3){
												Player p = Bukkit.getPlayer(args[1]);
												if(p != null){
													if(p.isOnline()){
														plugin.getGridPlayer(p).setBTC(plugin.getGridPlayer(p).getBTC() + Integer.parseInt(args[2]));
														sender.sendMessage("Gave "+args[2]+ "Bitcoin to "+p.getName());
													}else{
														sender.sendMessage("Player isn't online!");
													}
												}else{
													sender.sendMessage("Player wasn't found!");
												}
											}else{
												sender.sendMessage(plugin.getMessages().wrongCommand());
											}
										}else{
											if(args[0].equalsIgnoreCase("remSystem")){
												Bukkit.getPluginManager().registerEvents(new RemSystem(plugin, (Player)sender), plugin);
												sender.sendMessage("Click the system block to remove");
											}else{
												sender.sendMessage(plugin.getMessages().wrongCommand());
											}
										}
									}
								}
							}
						}
					}
				}
			}else{
				sender.sendMessage(plugin.getMessages().noPermission());
			}
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("gridspawn")){
			if(sender.hasPermission("thegrid.spawn")){
				if(sender instanceof Player){
					Player p = (Player)sender;
					
					if(plugin.getUserConfig().getSpawnLocation() != null){
						p.teleport(plugin.getUserConfig().getSpawnLocation());
					}else{
						p.sendMessage("�c[!] The Spawn location could not be found!");
					}
				}else{
					sender.sendMessage("�c[!] You must be a player to use that command!");
				}
			}else{
				sender.sendMessage(plugin.getMessages().noPermission());
			}
			return true;
		}
		return false;
	}
	
	private boolean isInteger(String s){
		try{
			Integer.parseInt(s);
			return true;
		}catch (Exception e){
			return false;
		}
	}
}
