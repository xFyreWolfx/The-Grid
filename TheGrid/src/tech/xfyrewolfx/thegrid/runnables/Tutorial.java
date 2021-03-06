package tech.xfyrewolfx.thegrid.runnables;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import tech.xfyrewolfx.thegrid.TheGrid;

public class Tutorial extends BukkitRunnable{
	
	TheGrid plugin;
	Player p;
	int i;
	List<String> msgs;
	public Tutorial(TheGrid c, Player pl){
		plugin=c;
		p=pl;
		msgs = new ArrayList<String>();
		msgs.add("�8[ �f>> �aconnected �8] �7Welcome to �aThe Grid�7; this is a �eSparring Program.");
		msgs.add("�8[ �2Your Goal �8] �7is to level-up and enhance your skills as a hacker.");
		msgs.add("�8[ �3EXP �8] �7is earned by hacking things. Collect �3EXP �7to level-up.");
		msgs.add("�8[ �6Bitcoin �8] �7is used to buy new viruses, items, and more.");
		msgs.add("�8[ �fEquipment �8] �7You have several tools at your disposal to use in �aThe Grid.");
		msgs.add("�8[ �eSystem �8] �7Your �eSystem �7is your PC. Use it to hack and charge your �2Battery.");
		msgs.add("�8[ �2Battery �8] �7Keep it charged. Without it, you cannot use your �eSystem.");
		msgs.add("�8[ �cViruses �8] �7are your hacking tools. They generate �3EXP �7and �6Bitcoin.");
		msgs.add("�8[ �6Firewall �8] �7Can help prevent other players from hacking your �eSystem.");
		msgs.add("�8[ �5Rules �8] �7No Minecraft hacks! Play fair, please!");
		msgs.add("�8[ �fYou Are Ready �8] �7Hack players, company systems, and more. Have fun!");
		msgs.add("�8[ �ki�fLoading�8�ki�8 ] �7Porting you to �aThe Grid...");
		i=0;
		
		plugin.getGridPlayer(p).setIsTutorial(true);
	}
	
	public void run(){
		if(p != null && p.isOnline()){
			if(i < (msgs.size()-1)){
				
				if(i==0){
					if(plugin.getUserConfig().getDoTutorialPotionEffects())
						p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,1650,1));
				}
				
				p.sendTitle(msgs.get(i).substring(0, msgs.get(i).indexOf("]")+1), msgs.get(i).substring(msgs.get(i).indexOf("]")+1), 10, 10, 90);
				i++;
			}else{
				this.endTutorial();
			}
		}else{
			this.cancel();
		}
	}
	
	private void endTutorial(){
		Location sl = plugin.getUserConfig().getSpawnLocation();
		if(sl != null){
			p.teleport(sl);
		}
		
		p.setLevel(plugin.getGridPlayer(p).getBattery());
		new Battery(plugin, p).runTaskTimer(plugin, 600, 600);
		p.getInventory().setContents(plugin.getGridPlayer(p).getInventoryItems());
		plugin.giveNewScoreboard(p);
		
		plugin.getGridPlayer(p).setIsTutorial(false);
		
		this.cancel();
	}
}
