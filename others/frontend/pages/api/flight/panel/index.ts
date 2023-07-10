import type { NextApiRequest, NextApiResponse } from 'next'
import { httpConstants } from "@config/constants/httpConstants"
import FlightManagerRestClient from "@services/FlightManagerRestClient"


export default async function apiResponse(request: NextApiRequest, response: NextApiResponse): Promise<void> {
	const { method, query } = request

	try {
		const flightManagerRestClient = new FlightManagerRestClient()

		switch (request?.method) {
			case "GET":
				const res = await flightManagerRestClient.test()

				return response.status(httpConstants.status.OK).json(
					{
						success: !!res,
						query: query,
						method: method,
						data: res
					}
				)

			default:
				return response.status(httpConstants.status.UNAUTHORIZED).json(
					{
						success: false,
						query: query,
						method: method,
						message: "Unauthorized"
					}
				)
		}
	}
	catch ({ message }) {
		return response.status(httpConstants.status.NOT_FOUND).json(
			{
				success: false,
				message: message
			}
		)
	}
}
