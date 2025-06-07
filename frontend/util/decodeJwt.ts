export interface JwtPayload {
    sub: string;
    iat: number;
    exp: number;
}

export function decodeJwt(token: string): JwtPayload | null {
    try {
        const payloadBase64 = token.split('.')[1];
        const payloadJson = atob(payloadBase64);
        return JSON.parse(payloadJson);
    } catch (error) {
        console.error("Invalid JWT format:", error);
        return null;
    }
}
