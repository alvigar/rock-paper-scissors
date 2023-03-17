import { FIGURE } from "./figure";
import { Movement } from "./movement";
import { PLAYER } from "./player";

export class Game {
    id: number | undefined;
    user: string | undefined;
    winner: boolean | undefined;
    winnerPlayer: PLAYER | null;
    movements: Movement[];
    tuple?: { player: FIGURE, machine: FIGURE }[];

    constructor() {
        this.id = undefined;
        this.user = undefined;
        this.winner = undefined;
        this.winnerPlayer = null;
        this.movements = [];
    }
}
