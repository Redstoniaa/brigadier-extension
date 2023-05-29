package brigadierextension.api.simplecommands;

import brigadierextension.api.simplecommands.providers.ClientCommandContextProvider;
import brigadierextension.api.simplecommands.providers.ServerCommandContextProvider;
import brigadierextension.api.simplecommands.providers.UniversalCommandContextProvider;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.server.command.ServerCommandSource;

public class SimpleCommandManager {
    public static SimpleLiteralArgumentBuilder<ServerCommandSource> literal(String literal) {
        return SimpleLiteralArgumentBuilder.literal(literal);
    }

    @SuppressWarnings("unchecked")
    public static <S> void setProviderContext(CommandContext<S> context) {
        UniversalCommandContextProvider.setContext(context);

        S source = context.getSource();
        if (source instanceof ServerCommandSource) {
            ServerCommandContextProvider.setContext((CommandContext<ServerCommandSource>) context);
        } else if (source instanceof FabricClientCommandSource) {
            ClientCommandContextProvider.setContext((CommandContext<FabricClientCommandSource>) context);
        }
    }

    public static void clearProviderContext() {
        UniversalCommandContextProvider.clearContext();
        ServerCommandContextProvider.clearContext();
        ClientCommandContextProvider.clearContext();
    }
}
