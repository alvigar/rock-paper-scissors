export interface UserDTO {
    username: string;
    password: string;
    token?: string;
    roles?: string[];
    enabled?: boolean;
    roleUSER?: boolean;
    roleADMIN?: boolean;
}