package dev.doctor4t.ratatouille.entity;

import dev.doctor4t.ratatouille.components.RatatouilleComponents;
import dev.doctor4t.ratatouille.components.RecoveryPosComponent;
import dev.doctor4t.ratatouille.index.RatatouilleEntities;
import dev.doctor4t.ratatouille.util.PlayerClientHead;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class PlayerHeadEntity extends Entity {
    private static final TrackedData<String> PLAYER_NAME = DataTracker.registerData(PlayerHeadEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Optional<UUID>> PLAYER_UUID = DataTracker.registerData(PlayerHeadEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private static final UUID araUUID = UUID.fromString("1ece513b-8d36-4f04-9be2-f341aa8c9ee2");
    private final SimpleInventory inventory = new SimpleInventory(54);
    private PlayerEntity player;

    public PlayerHeadEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public PlayerHeadEntity(World world, PlayerEntity player) {
        super(RatatouilleEntities.PLAYER_HEAD, world);
        this.setCustomName(player.getName());
        this.setPlayerUuid(player.getUuid());
        this.setPlayerName(player.getGameProfile().getName());
        this.setPosition(player.getPos());
        // save inventory
        for (int i = 0; i < player.getInventory().size(); i++) {
            this.inventory.addStack(player.getInventory().getStack(i));
        }
        // save trinkets
//        TrinketsApi.getTrinketComponent(player).ifPresent(trinkets -> {
//            for (Pair<SlotReference, ItemStack> slotReferenceItemStackPair : trinkets.getAllEquipped()) {
//                this.inventory.addStack(slotReferenceItemStackPair.getRight());
//            }
//        });
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        if (this.getPlayerUuid().isPresent()) {
            UUID uuid = this.getPlayerUuid().get();
            if (uuid.equals(araUUID)) {
                ClientPlayerEntity player = MinecraftClient.getInstance().player;
                return player != null && player.getUuid().equals(araUUID);
            }
        }
        return super.shouldRender(cameraX, cameraY, cameraZ);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(PLAYER_NAME, "rip bozo");
        this.dataTracker.startTracking(PLAYER_UUID, Optional.empty());
    }

    public String getPlayerName() {
        return this.dataTracker.get(PLAYER_NAME);
    }

    public void setPlayerName(String playerName) {
        this.dataTracker.set(PLAYER_NAME, playerName);
    }

    public Optional<UUID> getPlayerUuid() {
        return this.dataTracker.get(PLAYER_UUID);
    }

    public void setPlayerUuid(UUID playerUuid) {
        this.dataTracker.set(PLAYER_UUID, Optional.of(playerUuid));
    }

    @Override
    public boolean isGlowing() {
        if (this.getWorld().isClient() && PlayerClientHead.isOwned(this)) return true;
        return super.isGlowing();
    }

    @Override
    public void tick() {
        if (this.age % 80 == 0) {
            if (this.player == null) {
                if (this.getPlayerUuid().isPresent()) {
                    this.player = this.getWorld().getPlayerByUuid(this.getPlayerUuid().get());
                }
            }
            if (this.player != null) {
                if (this.player.getWorld() != this.getWorld()) {
                    this.player = null;
                } else {
                    RecoveryPosComponent pos = RatatouilleComponents.RECOVERY_POS_COMPONENT.get(this.player);
                    pos.setDeathPos(this.getBlockPos());
                }
            }
        }
        if (!this.getWorld().isClient && this.age % 400 == 0) {
            if (this.inventory.isEmpty()) {
                this.kill();
            }
        }
        super.tick();
        this.prevX = this.getX();
        this.prevY = this.getY();
        this.prevZ = this.getZ();
        Vec3d vec3d = this.getVelocity();
        float f = this.getStandingEyeHeight() - 0.11111111f;
        if (this.isTouchingWater() && this.getFluidHeight(FluidTags.WATER) > (double) f) {
            this.applyWaterBuoyancy();
        } else if (this.isInLava() && this.getFluidHeight(FluidTags.LAVA) > (double) f) {
            this.applyLavaBuoyancy();
        } else if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
        }
        if (this.getWorld().isClient) {
            this.noClip = false;
        } else {
            this.noClip = !this.getWorld().isSpaceEmpty(this, this.getBoundingBox().contract(1.0E-7));
            if (this.noClip) {
                this.pushOutOfBlocks(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0, this.getZ());
            }
        }
        if (!this.isOnGround() || this.getVelocity().horizontalLengthSquared() > (double) 1.0E-5f || (this.age + this.getId()) % 4 == 0) {
            this.move(MovementType.SELF, this.getVelocity());
            float g = 0.98f;
            if (this.isOnGround()) {
                g = this.getWorld().getBlockState(new BlockPos(this.getBlockX(), this.getBlockY() - 1, this.getBlockZ())).getBlock().getSlipperiness() * 0.98f;
            }
            this.setVelocity(this.getVelocity().multiply(g, 0.98, g));
            if (this.isOnGround()) {
                Vec3d vec3d2 = this.getVelocity();
                if (vec3d2.y < 0.0) {
                    this.setVelocity(vec3d2.multiply(1.0, -0.5, 1.0));
                }
            }
        }
        this.velocityDirty |= this.updateWaterState();
        if (!this.getWorld().isClient && this.getVelocity().subtract(vec3d).lengthSquared() > 0.01) {
            this.velocityDirty = true;
        }
    }

    @Override
    public void kill() {
        for (int i = 0; i < this.inventory.size(); i++) {
            this.dropStack(this.inventory.getStack(i));
        }
        this.discard();
    }

    private void applyWaterBuoyancy() {
        Vec3d vec3d = this.getVelocity();
        this.setVelocity(vec3d.x * (double) 0.99f, vec3d.y + (double) (vec3d.y < (double) 0.06f ? 5.0E-4f : 0.0f), vec3d.z * (double) 0.99f);
    }

    private void applyLavaBuoyancy() {
        Vec3d vec3d = this.getVelocity();
        this.setVelocity(vec3d.x * (double) 0.95f, vec3d.y + (double) (vec3d.y < (double) 0.06f ? 5.0E-4f : 0.0f), vec3d.z * (double) 0.95f);
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public boolean shouldRenderName() {
        return true;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player.isSneaking()) {
            player.openHandledScreen(new SkullScreenFactory(this));
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (!this.getWorld().isClient) {
            if (player.getGameProfile().getName().equals(this.getPlayerName())) {
                for (int i = 0; i < this.inventory.size(); i++) {
                    if (!player.getInventory().insertStack(this.inventory.getStack(i))) {
                        player.dropStack(this.inventory.getStack(i));
                    } else {
                        this.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7f + 1.0f) * 2.0f);
                    }
                }
                this.discard();
            }
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        NbtList nbtList = nbt.getList("Inventory", 10);
        this.inventory.readNbtList(nbtList);
        if (nbt.contains("PlayerUuid")) {
            this.setPlayerUuid(nbt.getUuid("PlayerUuid"));
        }
        if (nbt.contains("PlayerName")) {
            this.setPlayerName(nbt.getString("PlayerName"));
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.put("Inventory", this.inventory.toNbtList());
        nbt.putString("PlayerName", this.getPlayerName());
        this.getPlayerUuid().ifPresent(uuid -> nbt.putUuid("PlayerUuid", uuid));
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return super.createSpawnPacket();
    }

    public SimpleInventory getInventory() {
        return this.inventory;
    }

    private record SkullScreenFactory(PlayerHeadEntity head) implements NamedScreenHandlerFactory {
        @Override
        public Text getDisplayName() {
            return this.head.getDisplayName().copy().append(Text.literal("'s Remains"));
        }

        @Override
        public @NotNull ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
            return GenericContainerScreenHandler.createGeneric9x6(syncId, inv, this.head.getInventory());
        }
    }
}
