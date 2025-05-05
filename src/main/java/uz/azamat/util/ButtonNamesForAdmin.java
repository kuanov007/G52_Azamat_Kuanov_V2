package uz.azamat.util;

import lombok.Getter;

@Getter
public enum ButtonNamesForAdmin {
    CHECK_HOMEWORK("Check homework"),
    SHOW_OLD_HOMEWORKS("Show old homeworks");

    private final String string;

    ButtonNamesForAdmin(String string) {
        this.string = string;
    }
}
