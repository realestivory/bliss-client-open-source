#include "gui.h"

#include <thread>

int __stdcall wWinMain(
    HINSTANCE instance,
    HINSTANCE previousInstance,
    PWSTR arguments,
    int commandShow)
{
    // create gui
    gui::CreateHWindow("Launcher");
    gui::CreateDevice();
    gui::CreateImGui();

    bool active = true;

    while (gui::isRunning)
    {
        gui::BeginRender();
        gui::Render(active); 
        gui::EndRender();

        std::this_thread::sleep_for(std::chrono::milliseconds(5));
    }

    // destroy gui
    gui::DestroyImGui();
    gui::DestroyDevice();
    gui::DestroyHWindow();

    return EXIT_SUCCESS;
}
