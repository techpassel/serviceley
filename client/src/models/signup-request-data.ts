export class SignupRequestData {
    firstName!: string;
    lastName!: string;
    email!: string;
    phone: number | null = null;
    password!: string;
}