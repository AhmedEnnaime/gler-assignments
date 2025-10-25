export interface IForecast {
    id?: number,
    forecastDate: string,
    maxTemperature: number,
    maxHumidity: number,
    maxWindSpeed: number,
    createdAt?: string
}