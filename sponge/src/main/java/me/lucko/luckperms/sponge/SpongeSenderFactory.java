/*
 * This file is part of LuckPerms, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package me.lucko.luckperms.sponge;

import me.lucko.luckperms.common.commands.sender.SenderFactory;
import me.lucko.luckperms.common.constants.Constants;
import me.lucko.luckperms.common.plugin.LuckPermsPlugin;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;

import io.github.mkremins.fanciful.FancyMessage;

import java.util.UUID;

public class SpongeSenderFactory extends SenderFactory<CommandSource> {
    public SpongeSenderFactory(LuckPermsPlugin plugin) {
        super(plugin);
    }

    @Override
    protected String getName(CommandSource source) {
        if (source instanceof Player) {
            return source.getName();
        }
        return Constants.CONSOLE_NAME;
    }

    @Override
    protected UUID getUuid(CommandSource source) {
        if (source instanceof Player) {
            return ((Player) source).getUniqueId();
        }
        return Constants.CONSOLE_UUID;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void sendMessage(CommandSource source, String s) {
        source.sendMessage(TextSerializers.LEGACY_FORMATTING_CODE.deserialize(s));
    }

    @Override
    protected void sendMessage(CommandSource source, FancyMessage message) {
        try {
            source.sendMessage(TextSerializers.JSON.deserialize(message.exportToJson()));
        } catch (Exception e) {
            sendMessage(source, message.toOldMessageFormat());
        }
    }

    @Override
    protected boolean hasPermission(CommandSource source, String node) {
        return source.hasPermission(node);
    }
}
