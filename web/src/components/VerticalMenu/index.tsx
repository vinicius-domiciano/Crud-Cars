import React from "react";
import { useNavigate, useLocation } from "react-router-dom";

import styles from "./style.module.css";

export default function VerticalMenu() {
    const navigate = useNavigate();
    const location = useLocation();

    const selectedPage = (path: string): string => {
        return location.pathname === path ? styles.selectedMenu : "";
    };

    return (
        <div id={styles.menuContainer}>
            <button
                className={`${styles.buttonMenu} ${selectedPage("/car")}`}
                style={{
                    backgroundImage: "url(/icons/svg/car-menu-vertical.svg)",
                }}
                onClick={() => navigate("/car")}
            />
            <button
                className={`${styles.buttonMenu} ${selectedPage("/company")}`}
                style={{
                    backgroundImage:
                        "url(/icons/svg/company-menu-vertical.svg)",
                }}
                onClick={() => navigate("/company")}
            />
        </div>
    );
}
