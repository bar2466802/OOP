package filesprocessing.orders;

/**
 * Class for creating a new order object for the file processing operation
 * @author Bar Melinarskiy
 * @version 8/9/20
 */
public class FilesOrderFactory
{
	public static FilesOrder createOrder(OrderCommand orderType)
	{
		switch(orderType)
		{
		case SIZE:
			return new OrderBySize();
		case TYPE:
			return new OrderByType();
		case ABS:
		default:
			return new OrderByName();
		}
	}
}
