package com.wexalian.jmagnet.internal;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

public class TestPlugins {
    public static void main(String[] args) {
        Plugin plugin = initPlugin();
        System.out.println();
    }
    
    private static Plugin initPlugin() {
        try {
            //Curent ModuleLayer is usually boot layer. but it can be different if you are using multiple layers
            ModuleLayer currentModuleLayer = TestPlugins.class.getModule().getLayer(); //ModuleLayer.boot();
            final Set<Path> modulePathSet = Set.of(Path.of("plugins/test_plugin.jar"));
            //ModuleFinder to find modules
            final ModuleFinder moduleFinder = ModuleFinder.of(modulePathSet.toArray(new Path[0]));
            //I really dont know why does it requires empty finder.
            final ModuleFinder emptyFinder = ModuleFinder.of();
            //ModuleNames to be loaded
            final Set<String> moduleNames = moduleFinder.findAll()
                                                        .stream()
                                                        .map(moduleRef -> moduleRef.descriptor().name())
                                                        .collect(Collectors.toSet());
            // Unless you want to use URLClassloader for tomcat like situation, use Current Class Loader
            final ClassLoader loader = TestPlugins.class.getClassLoader();
            //Derive new configuration from current module layer configuration
            final Configuration configuration = currentModuleLayer.configuration().resolveAndBind(moduleFinder, emptyFinder, moduleNames);
            //New Module layer derived from current modulee layer
            final ModuleLayer moduleLayer = currentModuleLayer.defineModulesWithOneLoader(configuration, loader);
            //find module and load class
            final Class<?> controllerClass = moduleLayer.findModule("com.wexalian.jmagnet.testplugin")
                                                        .get()
                                                        .getClassLoader()
                                                        .loadClass("com.wexalian.jmagnet.testplugin.TestPlugin");
            //create new instance of Implementation, in this case org.util.npci.coreconnect.CoreController implements org.util.npci.api.BankController
            final Plugin plugin = (Plugin) controllerClass.getConstructors()[0].newInstance();
            return plugin;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
