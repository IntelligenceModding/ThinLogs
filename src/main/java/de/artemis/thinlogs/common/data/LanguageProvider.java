package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.registration.ModBlocks;
import de.artemis.thinlogs.common.registration.ThinLogSet;
import net.minecraft.data.PackOutput;

public class LanguageProvider extends net.neoforged.neoforge.common.data.LanguageProvider {
    public LanguageProvider(PackOutput packOutput, String locale) {
        super(packOutput, ThinLogs.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.thinlogs", "Artemis' Thin Logs");

        for (ThinLogSet set : ModBlocks.allSets()) {
            add(set.thinBlock().get(), "Thin " + set.definition().displayName());
            add(set.strippedThinBlock().get(), "Thin " + set.definition().strippedDisplayName());
        }
    }
}
