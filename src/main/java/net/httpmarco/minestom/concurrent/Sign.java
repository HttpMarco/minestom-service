package net.httpmarco.minestom.concurrent;

import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.tag.Tag;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;
import org.jglrxavpok.hephaistos.parser.SNBTParser;

import java.io.StringReader;
import java.util.Collection;
import java.util.List;

public final class Sign {

    private static final BlockHandler signHandler = new BlockHandler() {
        @Override
        public @NotNull Collection<Tag<?>> getBlockEntityTags() {
            return List.of(Tag.String("Text1"), Tag.String("Text2"), Tag.String("Text3"), Tag.String("Text4"));
        }

        @Override
        public @NotNull NamespaceID getNamespaceId() {
            return NamespaceID.from("minecraft:sign");
        }
    };

    private final Point pos;
    private final Instance instance;
    private final String[] lines = new String[4];

    public Sign(Point pos, Instance instance) {
        this.pos = pos;
        this.instance = instance;
    }

    public void setLine(int id, String input) {
        this.lines[id] = input;
    }

    public void update() {
        final NBTCompound data;
        try {
            data = (NBTCompound) new SNBTParser(new StringReader("{\"Text1\":\"{\\\"text\\\":\\\"" + lines[0] + "\\\"}\"," + "\"Text2\":\"{\\\"text\\\":\\\"" + lines[1] + "\\\"}\",\"Text3\":\"{\\\"text\\\":\\\"" + lines[2] + "\\\"}\",\"Text4\":\"{\\\"text\\\":\\\"" + lines[3] + "\\\"}\"}")).parse();
            instance.setBlock(pos, Block.OAK_WALL_SIGN.withHandler(signHandler).withNbt(data));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
