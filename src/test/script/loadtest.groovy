import groovyx.net.http.RESTClient

def main() {
    createTransaction(10L, 10D, "cars");

    println(getTransaction(10L))

    // TODO: Write load test
}

def createTransaction(long transactionId, double amount, String type) {
    def client = new RESTClient('http://localhost:8080')
    def response = client.put(
            path: '/transactionservice/transaction/' + transactionId,
            body: [amount: amount, type: type],
            requestContentType: 'application/json'
    )

    return response
}

def createTransactionWithParent(long transactionId, double amount, String type, Long parentId) {
    def client = new RESTClient('http://localhost:8080')
    def response = client.put(
            path: '/transactionservice/transaction/' + transactionId,
            body: [amount: amount, type: type, parent_id: parentId],
            requestContentType: 'application/json'
    )

    return response
}

def getTransaction(long transactionId) {
    def client = new RESTClient('http://localhost:8080')
    def transaction = client.get(
            path: '/transactionservice/transaction/' + transactionId,
            requestContentType: 'application/json'
    )

    return transaction
}

main()