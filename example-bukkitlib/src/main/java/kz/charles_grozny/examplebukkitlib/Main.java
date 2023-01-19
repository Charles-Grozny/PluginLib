package kz.charles_grozny.examplebukkitlib;

import kz.charles_grozny.bukkitlib.command.CommandManager;
import kz.charles_grozny.examplebukkitlib.data.ConfigData;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 23.02.2022 | 21:44 ⭐
 */
public final class Main extends JavaPlugin {


    @Override
    public void onEnable() {
        ConfigData configData = ConfigData.create(this);

        CommandManager.register(this, configData);
        System.out.println("go");
























        // если хочешь в Manager передать свои классы, и в классе команды их получить
        // ManagerM - создан в плагине
   //     CommandManager.register(ManagerImpl.create(this, (getClass().getPackageName() + ".command"), configData));

        // либа юзает default manager impl, там уже я прописал путь к командам, у меня в плагинах они всегда будут в пакете ".command"
        // опять же если что могу сам переопределить(ниже)
        // без конфига
    //    CommandManager.register(this);

        // ManagerDefault, переопределил путь до пакета команд
        // без конфига
        //   CommandManager.register(this, getClass().getPackageName() + ".command");

        // ManagerDefault, если путь ".command" то могу просто конфиг класс передать
    //    CommandManager.register(this, configData);


        // ну в ManagerDefault это максимум, что можно передать, если хочешь что-то ещё, то создавай в плагине свой ManagerImpl, как в 1 примере
      //  CommandManager.register(this, getClass().getPackageName() + ".command", configData);

    }

    @Override
    public void onDisable() {
        // SimplyCommandManager.unregister(this);
    }
}