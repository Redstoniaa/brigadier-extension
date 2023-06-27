package flora.command.builder;

import com.mojang.brigadier.tree.CommandNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandTreeAssembly {
    public static <S> CommandNode<S> build(TreeBuilder<S, ?> rootBuilder) {
        List<TreeBuilder<S, ?>> floatingBuilders = rootBuilder.collectChildren();
        redirectKeysFirst(floatingBuilders);

        Map<TreeBuilder<S, ?>, CommandNode<S>> blueprint = new HashMap<>();
        Map<String, CommandNode<S>> redirectKeyMap = new HashMap<>();

        for (TreeBuilder<S, ?> treeBuilder : floatingBuilders) {
            CommandNode<S> node;
            if (treeBuilder.redirectTo != null)
                node = treeBuilder.build(redirectKeyMap.get(treeBuilder.redirectTo));
            else
                node = treeBuilder.build();

            blueprint.put(treeBuilder, node);

            if (treeBuilder.redirectFrom != null)
                redirectKeyMap.put(treeBuilder.redirectFrom, node);
        }

        return rootBuilder.assemble(blueprint);
    }

    /**
     * Sort {@link List} of {@link TreeBuilder}s, with the ones with redirect keys first.
     */
    public static <S> void redirectKeysFirst(List<TreeBuilder<S, ?>> list) {
        list.sort((a,b) -> Boolean.compare(a.redirectFrom != null, b.redirectFrom != null));
    }
}
