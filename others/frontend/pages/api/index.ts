import type { NextApiRequest, NextApiResponse } from 'next'
import { httpConstants } from "@config/constants/httpConstants"


export default async function apiResponse(request: NextApiRequest, response: NextApiResponse): Promise<void> {
	const { headers, method, query, body, statusCode, statusMessage, url, cookies } = request

	try {
		return response.status(httpConstants.status.OK).json(
			{
				success: false,
				url: url,
				headers: headers,
				method: method,
				query: query,
				body: body,
				cookies: cookies,
				statusCode: statusCode,
				statusMessage: statusMessage,
				message: "OK"
			}
		)
	}
	catch ({ message }) {
		return response.status(httpConstants.status.INTERNAL_SERVER_ERROR).json(
			{
				success: false,
				message: message
			}
		)
	}
}
