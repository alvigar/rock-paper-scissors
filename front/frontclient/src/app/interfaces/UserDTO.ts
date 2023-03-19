export interface UserDTO {
    username: string;
    password: string;
    token?: string;
    roles?: string[];
}