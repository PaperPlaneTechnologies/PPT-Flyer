<?php
	require_once('stripe/init.php');
//get keys from stripe account at ready to launch
	$stripe = array(
	  "secret_key"      => "sk_test_iBZTNgiLoUegO68RC92UJpkR",
	  "publishable_key" => "pk_test_mphNAitqRb6kcWPVJaM8VJ8i"
	);

	\Stripe\Stripe::setApiKey($stripe['secret_key']);
?>
