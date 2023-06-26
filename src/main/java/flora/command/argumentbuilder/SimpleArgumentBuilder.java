package flora.command.argumentbuilder;

import flora.command.tree.simplecommands.SimpleCommand;
import com.mojang.brigadier.builder.ArgumentBuilder;

/**
 * Represents an {@link ArgumentBuilder} that can execute a {@link SimpleCommand}.
 */
public interface SimpleArgumentBuilder<S, T extends ArgumentBuilder<S, T>> {
    T executes(final SimpleCommand<S> simpleCommand);
}