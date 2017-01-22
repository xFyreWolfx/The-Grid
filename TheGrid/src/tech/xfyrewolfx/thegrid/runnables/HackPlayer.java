package tech.xfyrewolfx.thegrid.runnables;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import tech.xfyrewolfx.thegrid.TheGrid;
import tech.xfyrewolfx.thegrid.apis.TitleAPI;

public class HackPlayer extends BukkitRunnable{
	private TheGrid plugin;
	private Player t;
	private Player p;
	private int ticks;
	private String virus;
	private Random r;
	public HackPlayer(TheGrid c, Player target, Player pl, String vir){
		plugin=c;
		t=target;
		p=pl;
		ticks=10;
		virus=vir;
		r = new Random();
	}
	
	public void run(){
		if(!p.isOnline() || p==null){
			this.cancel();
			return;
		}
		
		if(!t.isOnline() || t==null){
			this.cancel();
			return;
		}
		
		if(ticks>0){
			if(p.getLocation().distance(t.getLocation())>15){
				p.sendMessage(plugin.getMessages().outOfRange());
				this.cancel();
				return;
			}
			
			if(p.getLevel()<1){
				p.sendMessage(plugin.getMessages().batteryDepleted());
				this.cancel();
				return;
			}
			
			// Create a Title progress bar
			StringBuilder sb = new StringBuilder();
			String bar="";
			sb.append("§cUPLOAD "+virus+" §f[");
			
			for(int i=0; i<(10-ticks); i++){
				sb.append("§a|");
			}
			for(int i=0; i<ticks; i++){
				sb.append("§f|");
			}
			
			sb.append("§f]");
			bar=sb.toString();
			TitleAPI.sendTitle(p, 0, 0, 25, "", bar);
		}else{
			new Cooldown(plugin, p).runTaskTimer(plugin, 20, 20);
			
			// TODO tell the target they got hacked
			t.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,100,1));
			t.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,100,1));
			
			new Cooldown(plugin, t).runTaskTimer(plugin, 20, 20);
			awardPlayer();
			this.cancel();
		}
	}
	
	private void awardPlayer(){
		if(virus.equals("SHUTDOWN.vbs")){
			int exp = generateEXP(plugin.getGPlayer(t).getLevel(), 1);
			plugin.getGPlayer(p).addExp(exp);
			p.sendMessage(plugin.getMessages().gotEXP(exp));
		}
		
		if(virus.equals("SQL Slammer")){
			int exp = generateEXP(plugin.getGPlayer(t).getLevel(), 1);
			plugin.getGPlayer(p).addExp(exp);
			p.sendMessage(plugin.getMessages().gotEXP(exp));
		}
		
		if(virus.equals("Cryptolocker")){
			int btc = r.nextInt(3)+1;
			p.sendMessage(plugin.getMessages().gotBTC(btc));
		}
		
		if(virus.equals("DDoS Attack")){
			int exp = generateEXP(plugin.getGPlayer(t).getLevel(), 2);
			plugin.getGPlayer(p).addExp(exp);
			p.sendMessage(plugin.getMessages().gotEXP(exp));
		}
		
		if(virus.equals("Adware")){
			int btc = r.nextInt(2)+1;
			p.sendMessage(plugin.getMessages().gotBTC(btc));
		}
		
		if(virus.equals("Killdisc")){
			int exp = generateEXP(plugin.getGPlayer(t).getLevel(), 2);
			plugin.getGPlayer(p).addExp(exp);
			p.sendMessage(plugin.getMessages().gotEXP(exp));
		}
	}
	
	private int generateEXP(int f, int m){
		return (f * m)+r.nextInt(30);
	}
}