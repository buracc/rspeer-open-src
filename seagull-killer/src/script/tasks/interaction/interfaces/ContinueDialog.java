package script.tasks.interaction.interfaces;

import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.script.task.Task;

public class ContinueDialog extends Task {

    @Override
    public int execute() {
        Dialog.processContinue();
        return 3000;
    }

    @Override
    public boolean validate() {
        return Dialog.canContinue();
    }

    @Override
    public String toString() {
        return "Dialog";
    }
}
