package kirbyandfriends.entities;


import java.util.Random;

import kirbyandfriends.KirbyMod;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;



public class EntityKirby extends EntityGolem
{
	 Random r = new Random();
 	int i = (r.nextInt(10)); 
 	
    public EntityKirby(World world)
    {
        super(world);
        this.setSize(0.5F, 0.5F);
        this.getNavigator().setAvoidsWater(false);
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(6, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
        this.tasks.addTask(3, new EntityAITempt(this, 1.1D, Items.wheat, false));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, true, IMob.mobSelector));
    }
    
	 private static final ResourceLocation Black= new ResourceLocation(KirbyMod.modid + ":textures/entities/ShadowKirby.png"); 
	   private static final ResourceLocation Default= new ResourceLocation(KirbyMod.modid + ":textures/entities/PinkKirby.png");
	   private static final ResourceLocation White= new ResourceLocation(KirbyMod.modid + ":textures/entities/WhiteKirby.png");
	   private static final ResourceLocation Red= new ResourceLocation(KirbyMod.modid + ":textures/entities/RedKirby.png");
	   private static final ResourceLocation Blue= new ResourceLocation(KirbyMod.modid + ":textures/entities/BlueKirby.png");
	   private static final ResourceLocation Green = new ResourceLocation(KirbyMod.modid + ":textures/entities/GreenKirby.png");
	   private static final ResourceLocation Purple = new ResourceLocation(KirbyMod.modid + ":textures/entities/PurpleKirby.png");
	   private static final ResourceLocation Yellow = new ResourceLocation(KirbyMod.modid + ":textures/entities/YellowKirby.png");
	   private static final ResourceLocation Brown = new ResourceLocation(KirbyMod.modid + ":textures/entities/BrownKirby.png");
	   
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(20, Integer.valueOf(0));
    }
    
    
    public void setHorseType(int i)
    {
        this.dataWatcher.updateObject(20, Integer.valueOf(i)); 
    }
    
    protected boolean canDespawn()
    {
        return true;
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean isAIEnabled()
    {
        return true;
    }

    protected void updateAITasks()
    {
        super.updateAITasks();
    }

    
    
    public void setHorseVariant(int i)
    {
        this.dataWatcher.updateObject(20, Integer.valueOf(i));
    }
    

    public int getHorseVariant()
    {
        return this.dataWatcher.getWatchableObjectInt(20);
    }
    
    public ResourceLocation getresource(){ 
    	if (i== 1) {
			return Default;
		}
		if (i==2){
			return Red;
		}
		if (i==3){
			return Blue;
		}
		if (i==4){
			return Yellow;
		}
		if (i==5){
			return Purple;
		}
		if (i==6){
			return Green;
		}
		if (i==7){
			return White;
		}
		if (i==8){
			return Black;
		}
		else
		 return	Brown;   
    }
    
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
    }


    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
   

    protected Item getDropItem()
    {
        return KirbyMod.wishstar;
    }


 
    

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "kirbyandfriends:mob.KirbySound2";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "kirbyandfriends:mob.KirbySound1";
    }
    
    public boolean attackEntityAsMob(Entity entity)
    {
        int i = 5;
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)i);
    }


    protected float getSoundVolume()
    {
        return 0.4F;
    }
    
    
  
    
    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "kirbyandfriends:mob.KirbyDeath";
    }

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound("mob.sheep.step", 0.15F, 1.0F);
    }
    
    public EntityKirby createChild(EntityAnimal par1EntityAnimal)
    {
      return new EntityKirby(worldObj);
    }


    public EntityKirby createChild(EntityAgeable par1EntityAnimal)
    {
      return new EntityKirby(worldObj);
    }
   
    }
    
