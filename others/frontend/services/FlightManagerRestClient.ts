import axios, { AxiosInstance } from 'axios';
import axiosRetry from 'axios-retry';


export default class FlightManagerRestClient {
	private client: AxiosInstance;

	constructor() {
		const baseUrl = process.env.APP_URL;

		this.client = axios.create({
			baseURL: baseUrl,
			timeout: 1000, // 1s
		});

		axiosRetry(this.client, {
			retries: 3,
			retryDelay: (retryCount) => {
				return retryCount * 2000;
			},
		});
	}

	public async test(): Promise<any> {
		try {
			const { data } = await this.client.get('/check');
			return data;
		} catch (error) {
			return null;
		}
	}
}
