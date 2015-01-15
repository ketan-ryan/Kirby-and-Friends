package kirbyandfriends.entities;


import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

public class EntityKirby extends EntityAnimal 
{
	 private final InventoryCrafting field_90016_e = new InventoryCrafting(new Container()
	    {
	        public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	        {
	            return false;
	        }
	    }, 2, 1);
   
    /** Holds the RGB table of the kirby colors - in OpenGL glColor3f values - used to render the kirby colored fleece. */
    public static final float[][] fleeceColorTable = new float[][] {{1.0F, 1.0F, 1.0F}, {0.85F, 0.5F, 0.2F}, {0.7F, 0.3F, 0.85F}, {0.4F, 0.6F, 0.85F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.5F, 0.65F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.5F, 0.6F}, {0.5F, 0.25F, 0.7F}, {0.2F, 0.3F, 0.7F}, {0.4F, 0.3F, 0.2F}, {0.4F, 0.5F, 0.2F}, {0.6F, 0.2F, 0.2F}, {0.1F, 0.1F, 0.1F}};
    /**
     * Used to control movement as well as wool regrowth. Set to 40 on handleHealthUpdate and counts down with each
     * tick.
     */
    private int kirbyTimer;
    

    public EntityKirby(World world)
    {
        super(world);
        this.setSize(0.9F, 1.3F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.1D, Items.wheat, false));
      
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.field_90016_e.setInventorySlotContents(0, new ItemStack(Items.dye, 1, 0));
        this.field_90016_e.setInventorySlotContents(1, new ItemStack(Items.dye, 1, 0));
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

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (this.worldObj.isRemote)
        {
            this.kirbyTimer = Math.max(0, this.kirbyTimer - 1);
        }

        super.onLivingUpdate();
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }

    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
   

    protected Item getDropItem()
    {
        return Item.getItemFromBlock(Blocks.wool);
    }

    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte p_70103_1_)
    {
        if (p_70103_1_ == 10)
        {
            this.kirbyTimer = 40;
        }
        else
        {
            super.handleHealthUpdate(p_70103_1_);
        }
    }
    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer par1EntityPlayer)
    {
    	  ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();
    	  
    	  if (itemstack != null) {
    	  int i = BlockColored.func_150032_b(itemstack.getItemDamage());
          if (itemstack.getItem() == Items.dye && getFleeceColor() != i)
          {
        	  
        	  setFleeceColor(i);
              return true;
          }
          else
          {
              
          }
    }
    	  return super.interact(par1EntityPlayer);
    }
    
    @SideOnly(Side.CLIENT)
    public float func_70894_j(float p_70894_1_)
    {
        return this.kirbyTimer <= 0 ? 0.0F : (this.kirbyTimer >= 4 && this.kirbyTimer <= 36 ? 1.0F : (this.kirbyTimer < 4 ? ((float)this.kirbyTimer - p_70894_1_) / 4.0F : -((float)(this.kirbyTimer - 40) - p_70894_1_) / 4.0F));
    }

    @SideOnly(Side.CLIENT)
    public float func_70890_k(float p_70890_1_)
    {
        if (this.kirbyTimer > 4 && this.kirbyTimer <= 36)
        {
            float f1 = ((float)(this.kirbyTimer - 4) - p_70890_1_) / 32.0F;
            return ((float)Math.PI / 5F) + ((float)Math.PI * 7F / 100F) * MathHelper.sin(f1 * 28.7F);
        }
        else
        {
            return this.kirbyTimer > 0 ? ((float)Math.PI / 5F) : this.rotationPitch / (180F / (float)Math.PI);
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound p_70014_1_)
    {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setByte("Color", (byte)this.getFleeceColor());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound p_70037_1_)
    {
        super.readEntityFromNBT(p_70037_1_);
        this.setFleeceColor(p_70037_1_.getByte("Color"));
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

    public int getFleeceColor()
    {
        return this.dataWatcher.getWatchableObjectByte(16) & 15;
    }

    public void setFleeceColor(int p_70891_1_)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 240 | p_70891_1_ & 15)));
    }

  
   

   
		
    /**
     * This method is called when a kirby spawns in the world to select the color of kirby fleece.
     */
    public static int getRandomFleeceColor(Random p_70895_0_)
    {
        int i = p_70895_0_.nextInt(100);
        return i < 5 ? 15 : (i < 10 ? 7 : (i < 15 ? 8 : (i < 18 ? 12 : (p_70895_0_.nextInt(500) == 0 ? 6 : 0))));
    }

    public EntityKirby createChild(EntityAgeable p_90011_1_)
    {
        EntityKirby entitykirby = (EntityKirby)p_90011_1_;
        EntityKirby entitykirby1 = new EntityKirby(this.worldObj);
        int i = this.func_90014_a(this, entitykirby);
        entitykirby1.setFleeceColor(15 - i);
        return entitykirby1;
    }

    /**
     * This function applies the benefits of growing back wool and faster growing up to the acting entity. (This
     * function is used in the AIEatGrass)
     */
    

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
    {
        p_110161_1_ = super.onSpawnWithEgg(p_110161_1_);
        this.setFleeceColor(getRandomFleeceColor(this.worldObj.rand));
        return p_110161_1_;
    }

    private int func_90014_a(EntityAnimal p_90014_1_, EntityAnimal p_90014_2_)
    {
        int i = this.func_90013_b(p_90014_1_);
        int j = this.func_90013_b(p_90014_2_);
        this.field_90016_e.getStackInSlot(0).setItemDamage(i);
        this.field_90016_e.getStackInSlot(1).setItemDamage(j);
        ItemStack itemstack = CraftingManager.getInstance().findMatchingRecipe(this.field_90016_e, ((EntityKirby)p_90014_1_).worldObj);
        int k;

        if (itemstack != null && itemstack.getItem() == Items.dye)
        {
            k = itemstack.getItemDamage();
        }
        else
        {
            k = this.worldObj.rand.nextBoolean() ? i : j;
        }

        return k;
    }

    private int func_90013_b(EntityAnimal p_90013_1_)
    {
        return 15 - ((EntityKirby)p_90013_1_).getFleeceColor();
    } 
    }
    