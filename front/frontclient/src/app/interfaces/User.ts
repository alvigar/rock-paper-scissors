export class User {
    nickname?: string;
    password?: string;
    roles?: { id: number, roleName: string }[];
    enabled?: boolean;
    roleUSER?: boolean;
    roleADMIN?: boolean;
}