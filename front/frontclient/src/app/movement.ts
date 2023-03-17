import { FIGURE } from "./figure";
import { PLAYER } from "./player";

export class Movement {
    id: number | undefined;
    player: PLAYER | null;
    figure: FIGURE | null;

    constructor() {
        this.id = undefined;
        this.player = null;
        this.figure = null;
    }
}