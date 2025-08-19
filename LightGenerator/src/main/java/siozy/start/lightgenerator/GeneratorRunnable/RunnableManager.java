package siozy.start.lightgenerator.GeneratorRunnable;

import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class RunnableManager {
    private static final List<GeneratorRunnable> activeList = new ArrayList<>();

    public static void load(GeneratorRunnable runnable) {
        activeList.add(runnable);
    }

    public static void unload(GeneratorRunnable runnable) {
        activeList.remove(runnable);
    }

    public static void clear() {
        for (GeneratorRunnable generatorRunnable : activeList) {
            generatorRunnable.cancel();
        }
        activeList.clear();
    }

    public static GeneratorRunnable get(Block block) {
        for (GeneratorRunnable runnable : activeList) {
            if (block.equals(runnable.getBlock())) {
                return runnable;
            }
        }
        return null;
    }
}
